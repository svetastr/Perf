const fs = require('fs')
const puppeteer = require('puppeteer')
const lighthouse = require('lighthouse/lighthouse-core/fraggle-rock/api.js')

const waitTillHTMLRendered = async (page, timeout = 30000) => {
  const checkDurationMsecs = 1000;
  const maxChecks = timeout / checkDurationMsecs;
  let lastHTMLSize = 0;
  let checkCounts = 1;
  let countStableSizeIterations = 0;
  const minStableSizeIterations = 3;

  while(checkCounts++ <= maxChecks){
    let html = await page.content();
    let currentHTMLSize = html.length; 

    let bodyHTMLSize = await page.evaluate(() => document.body.innerHTML.length);

    //console.log('last: ', lastHTMLSize, ' <> curr: ', currentHTMLSize, " body html size: ", bodyHTMLSize);

    if(lastHTMLSize != 0 && currentHTMLSize == lastHTMLSize) 
      countStableSizeIterations++;
    else 
      countStableSizeIterations = 0; //reset the counter

    if(countStableSizeIterations >= minStableSizeIterations) {
      console.log("Fully Rendered Page: " + page.url());
      break;
    }

    lastHTMLSize = currentHTMLSize;
    await page.waitForTimeout(checkDurationMsecs);
  }  
};

async function captureReport() {
	const browser = await puppeteer.launch({args: ['--allow-no-sandbox-job', '--allow-sandbox-debugging', '--no-sandbox', '--disable-gpu', '--disable-gpu-sandbox', '--display', '--ignore-certificate-errors', '--disable-storage-reset=true']});
	//const browser = await puppeteer.launch({"headless": false, args: ['--allow-no-sandbox-job', '--allow-sandbox-debugging', '--no-sandbox', '--ignore-certificate-errors', '--disable-storage-reset=true']});
	const page = await browser.newPage();
	const baseURL = "http://localhost";
	
	await page.setViewport({"width":1920,"height":1080});
	await page.setDefaultTimeout(10000);
			
	const navigationPromise = page.waitForNavigation({timeout: 30000, waitUntil: ['domcontentloaded']});
	await page.goto(baseURL);
	await page.setDefaultTimeout(10000);
			
	const flow = await lighthouse.startFlow(page, {
		name: 'Shopizer',
		configContext: {
		  settingsOverrides: {
			throttling: {
			  rttMs: 40,
			  throughputKbps: 10240,
			  cpuSlowdownMultiplier: 1,
			  requestLatencyMs: 0,
			  downloadThroughputKbps: 0,
			  uploadThroughputKbps: 0
			},
			throttlingMethod: "simulate",
			screenEmulation: {
			  mobile: false,
			  width: 1920,
			  height: 1080,
			  deviceScaleFactor: 1,
			  disabled: false,
			},
			formFactor: "desktop",
			onlyCategories: ['performance'],
		  },
		},
	});

  	//================================NAVIGATE================================
    await flow.navigate(baseURL, {
		stepName: 'open main page'
		});
	await waitTillHTMLRendered(page);
  	console.log('main page is opened');	
	
	
	//================================SELECTORS================================
	
	const tableCat      = ".main-menu a[href='/category/tables']";
	const tableProduct  = ".product-img a[href='/product/olive-table']";
	const addtoCart     = "div[class='pro-details-cart btn-hover']";
	const cartIcon      = "i[class='pe-7s-shopbag']";
	const cartButton    = "a[href='/cart']";
	const clearCart     = "div[class='cart-clear']";
	const tableImg      = "img[src='http://localhost:8080/static/products/DEFAULT/table1/SMALL/console-1.jpg']"
	const checkoutBtn   = ".box-custom a[href='/checkout']";
	const billingDtls   = "div[class='billing-info-wrap']"
	
	//================================PAGE_ACTIONS================================
		
	await page.waitForSelector(tableCat)
		await flow.startTimespan({ stepName: 'open tables category' });
		await page.click(tableCat);
		await waitTillHTMLRendered(page);
        await page.waitForSelector(tableProduct);
    await flow.endTimespan();
    console.log('tables category opened');
	
	await flow.startTimespan({ stepName: 'open table page' });
		await page.click(tableProduct);
		await waitTillHTMLRendered(page);
        await page.waitForSelector(addtoCart);
    await flow.endTimespan();
    console.log('table page opened');
	
	await flow.startTimespan({ stepName: 'add table to cart' });
		await page.click(addtoCart);
		await flow.endTimespan();
    console.log('table added to cart');
	
	await flow.startTimespan({ stepName: 'open cart' });
		await page.click(cartIcon);
		await waitTillHTMLRendered(page);
		await page.click(cartButton);
		await waitTillHTMLRendered(page);
		await page.waitForSelector(clearCart);
	console.log('cart opened');
		
        await page.waitForSelector(tableImg);
		await flow.endTimespan();
    console.log('table is shown in cart');
	
	await flow.startTimespan({ stepName: 'open checkout' });
		await page.click(checkoutBtn);
		await waitTillHTMLRendered(page);
        await page.waitForSelector(billingDtls);
		await flow.endTimespan();
    console.log('checkout opened');

	//================================REPORTING================================
	const reportPath = __dirname + '/Shopizer_report.html';
	//const reportPathJson = __dirname + '/user-flow.report.json';

	//const report = flow.generateReport();
	//const reportJson = JSON.stringify(flow.getFlowResult()).replace(/</g, '\\u003c').replace(/\u2028/g, '\\u2028').replace(/\u2029/g, '\\u2029');
	
	fs.writeFileSync(reportPath, await flow.generateReport());
	//fs.writeFileSync(reportPathJson, reportJson);
	
    await browser.close();
}
captureReport();

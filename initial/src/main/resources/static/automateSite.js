async function theMain(args) {			
    let client;
    let testResult = 'passed';
    try {
       
        client = await window.CDP.CDP();
        
        const {Network, Page, Runtime} = client;
        
        
        //Network.requestWillBeSent((params) => {
        //    console.log(params.request.url);
        //});
        
        //Enable the events for the domains.
        //await Network.enable();
        await Page.enable();
		await Runtime.enable();
        //Navigate to the url
        let url = args.args;
        await Page.navigate({url});
        //Wait for the page to load
        await Page.loadEventFired();
        // Test       
        await Runtime.evaluate({expression: 'document.getElementsByTagName("button")[5].getAttribute("type")'}).then((res)=>{        	
        	if (res.result.value != 'submit') {
        		testResult = 'failed';
        	}
        });
        await Runtime.evaluate({expression: 'document.getElementsByTagName("button")[5].innerText'}).then((res)=>{        	
        	if (res.result.value != 'Sign up for GitHub') {
        		testResult = 'failed';
        	}
        });
    } catch (err) {
    	testResult='failed';
        console.error(err);
    } finally {
    	console.log(testResult);
        	fetch(args.res,{method:'post', headers: {
	        	    	      'Content-Type':'application/json'        	    	          
	        	    	       },body:testResult});
        if (client) {
            await client.close();
        }
    }
} 
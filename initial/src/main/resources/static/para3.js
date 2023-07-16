async function theMain(args) {	
	var reader;
    do {
		reader = await fetch('/jobs/readnumber');
		try {	
			var nums = await reader.json();
			var n1 = nums.n1;	
			var n2 = nums.n2;	
			console.log('Got '+n1+' and '+n2);
			var res = n1+n2;	
			console.log('sending answer: '+res);
	        await fetch(args.res,{method:'post', headers: {
	        	    	      'Content-Type':'application/json'        	    	          
	        	    	       },body:res})
        } catch(e) {
        	console.log('No more numbers ');
        	reader = null
        }
    } while(reader)
} 
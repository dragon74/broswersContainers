async function theMain(args) {	
		var r = mobilenet.load().then(model => {
            return new Promise(function(resolve, reject){
                const img = document.createElement('img');

                img.onload = function(){
                    // Classify the image.
                    resolve(model.classify(img))
                }

                img.onerror = function(err){
                    reject(err)
                }

                // get the image URL from the "img" argument
                img.src = args.args || ""
            })
        });
        r.then(pred=> {
        	console.log(pred[0].className);
        	fetch(args.res,{method:'post', headers: {
	        	    	      'Content-Type':'application/json'        	    	          
	        	    	       },body:pred[0].className});
        });
} 
async function theMain(args) {	
	var pdf = new jsPDF({
		orientation: 'p',
		unit: 'mm',
		format: 'a5',
		putOnlyUsedFonts:true
	});
	pdf.text("An Invoice Sample", 20, 20);
	pdf.text("Invoice sum: "+args.args, 20, 30);	
	pdf.save('Invoice.pdf');
} 
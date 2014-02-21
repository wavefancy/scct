/*
 * Compute Windowed critical value. The proportion of SNPs with value bigger than the user specified threshold.
 * 
 *
 */

var carrier = require('./carrier.js');

var win_size = 50; //Windowe size
var step = 50; // step for the sliding window.
var threshold = 2.0;
var val_index = 4;  // value index.
var critical = 0.95; //critical value.

if (process.argv.length != 6) {
	console.log("--------------------------")
	console.log("Compute_Windowed_critical_value, version 1.0, wavefancy@gmail.com")
	console.log("Usages:")
	console.log("parameter1(int): window size")
	console.log("parameter2(int): step size")
	console.log("parameter3(int): threshold")
	console.log("parameter4(int): value index.")
	console.log("[Column index start from 1.]")
	console.log("--------------------------")

	process.exit(-1);
};

win_size = parseInt(process.argv[2]);
step = parseInt(process.argv[3]);
threshold = parseFloat(process.argv[4]);
val_index = parseInt(process.argv[5]) -1 ;

if (isNaN(win_size)) {
	console.log("Plese set proper number for [window size]")
	process.exit(-1);
};

if (isNaN(step)) {
	console.log("Plese set proper number for [step]")
	process.exit(-1);
};

if (isNaN(threshold)) {
	console.log("Plese set proper number for [threshold]")
	process.exit(-1);
};

if (isNaN(val_index)) {
	console.log("Plese set proper number for [Column index]")
	process.exit(-1);
};

// console.log(val_index);

//compute results.
var all_results = []; //store all results.
var sec_array  = []; //section array.

process.stdin.resume();
var my_carrier = carrier.carry(process.stdin);

my_carrier.on('line',function (line) {
	// dataset splited by title, which start with #;
	if (line.substring(0,1) == '#') {
		// console.log("in--");

		computeNB();		
		
	}else if(line.length > 0){
		var arr = line.split(/\s+/);
		var t = parseFloat(arr[val_index]);
		if (!isNaN(t)) {
			sec_array.push(t);
		};
		
		// console.log(arr[val_index]);
	};
})

my_carrier.on('end',function(){
	computeNB();

	all_results.sort(function(a,b){return a-b;});

	// console.log(all_results);
	var c = all_results[Math.floor(all_results.length * critical)];

	// console.log(Math.floor(all_results.length * critical));
	console.log(c);
});

/**
* Compute the number of value with bigger than the critical value for each window.
**/
function computeNB(){

	if (sec_array.length > 0) {  //compute results.

			// console.log(sec_array);

			for (var i = 0; i <= sec_array.length-win_size ; i=i+step) {
				//compute the number of critical value.
				var nb = 0;
				for (var j = i; j < i+win_size; j++) {
					if (Math.abs(sec_array[j]) >= threshold) {
						nb++;
					};
				};
				all_results.push(nb);
				// console.log(nb);
			};

			//clear section array
			sec_array = [];
	}
}

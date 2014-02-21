/*
 * Power caculation for a list of single value.
 * 
 * Compute the proportion of values which are bigger or smaller than the threshold value user specified.
 */

var carrier = require('carrier');

var col_index = 4;  // column index for value to compute
var threshold = 0.95; //critical value.
var transform = 1; //critical value.
var direction = 1;

if (process.argv.length != 6) {
	console.log("--------------------------")
	console.log("Compute Power Single, version 1.0, wavefancy@gmail.com")
	console.log("Usages:")
	console.log("parameter1(int): column index")
	console.log("parameter2(float): threshold")
	console.log("parameter3(int): whether transformed to abolsulte value.")
	console.log("parameter4(int): smaller or bigger than threshold.")
	// console.log("parameter4(int): value index.")
	console.log("[Column index start from 1.]")
	console.log("[1 for do not transform, -1 for transform]")
	console.log("[1 for bigger than, -1 for smaller than threshold.]")
	console.log("--------------------------")

	process.exit(-1);
};

col_index = parseInt(process.argv[2])-1;
threshold = parseFloat(process.argv[3]);
transform = parseInt(process.argv[4]);
direction = parseInt(process.argv[5]);

if (isNaN(col_index)) {
	console.log("Plese set proper number for [column index]")
	process.exit(-1);
};

if (isNaN(threshold)) {
	console.log("Plese set proper number for [percentile]")
	process.exit(-1);
};

if (isNaN(transform)) {
	console.log("Plese set proper number for [transform action]")
	process.exit(-1);
};

if (isNaN(direction)) {
	console.log("Plese set proper number for [direction]")
	process.exit(-1);
};


// console.log(val_index);

//compute results.
var all_results = []; //store all results.

process.stdin.resume();
var my_carrier = carrier.carry(process.stdin);

my_carrier.on('line',function (line) {

		var arr = line.split(/\s+/);
		var t = parseFloat(arr[col_index]);
		if (!isNaN(t)) {
			if (transform > 0) {
				all_results.push(t);
			}else{
				all_results.push(Math.abs(t));
			};
		};
})

my_carrier.on('end',function(){
	// computeNB();

	var pass = 0;
	for (var i = 0; i < all_results.length; i++) {
		if (direction<0) {
			if (all_results[i] < threshold) {
				pass++;
			};
		}else{
			if (all_results[i] > threshold) {
				pass++;
			};
		};
	};


	// console.log(Math.floor(all_results.length * critical));
	console.log("Total: " + all_results.length);
	console.log("Effect:" + pass);
	console.log(pass/all_results.length);
});

// /**
// * Compute the number of value with bigger than the critical value for each window.
// **/
// function computeNB(){

// 	if (sec_array.length > 0) {  //compute results.

// 			// console.log(sec_array);

// 			for (var i = 0; i <= sec_array.length-win_size ; i=i+step) {
// 				//compute the number of critical value.
// 				var nb = 0;
// 				for (var j = i; j < i+win_size; j++) {
// 					if (Math.abs(sec_array[j]) >= threshold) {
// 						nb++;
// 					};
// 				};
// 				all_results.push(nb);
// 				// console.log(nb);
// 			};

// 			//clear section array
// 			sec_array = [];
// 	}
// }

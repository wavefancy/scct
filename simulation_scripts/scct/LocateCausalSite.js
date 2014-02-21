/*
 * Compute the probability of Locating causal site.
 *
 * The probability of the statistical score (iHS or BLS)  of causal site is in the top 3 of that of a positively selected window region.
 *
 * Using a windowed approach. A window assumed to be experienced natural selection if the number of value bigger than the threshold is more than the 0.95 critical value in neutral scenario.
 * >= threshold.
 * > critical value.
 * SNP with NA value will be removed automatically, except those SNP with selection acting on.
 */

var carrier = require('./carrier.js');

var flanking_size = 25; //flanking size
var threshold = 2.0;
var critical = 7; //critical value.
var pos = 0.5000000000;  // selection position
var pos_col = 1; //postion column
var val_col = 5; //value column.

var top = 3; // threshold for locating.
top--; //shift arr index.

if (process.argv.length != 9) {
	console.log("--------------------------")
	console.log("PowerCaculation, version 1.0, wavefancy@gmail.com")
	console.log("Usages:")
	console.log("parameter1(int): flanking size")
	console.log("parameter2(float): threshold value")
	console.log("parameter3(float): critical value")
	console.log("parameter4(String): position")
	console.log("parameter5(int): position column")
	console.log("parameter6(int): value column")
	console.log("parameter7(int): top_index")
	console.log("[Column index start from 1.]")
	console.log("--------------------------")

	process.exit(-1);
};

flanking_size = parseInt(process.argv[2]);
threshold = parseFloat(process.argv[3]);
critical = parseFloat(process.argv[4]);
pos = process.argv[5];
pos_col = parseInt(process.argv[6]) -1;
val_col = parseInt(process.argv[7]) -1;
top = parseInt(process.argv[8])

if (isNaN(flanking_size)) {
	console.log("Plese set proper number for [flanking size]")
	process.exit(-1);
};

if (isNaN(threshold)) {
	console.log("Plese set proper number for [threshold]")
	process.exit(-1);
};

if (isNaN(critical)) {
	console.log("Plese set proper number for [critical value]")
	process.exit(-1);
};

if (isNaN(pos_col)) {
	console.log("Plese set proper number for [Position column]")
	process.exit(-1);
};

if (isNaN(val_col)) {
	console.log("Plese set proper number for [value column]")
	process.exit(-1);
};

if (isNaN(top)) {
	console.log("Plese set proper number for [top_index]")
	process.exit(-1);
};
top--; //shift top index.

//compute results.
var total = 0; // total numer of location examined.
var ok = 0; // detected as selection region.

var val_array  = []; //value array.
var pos_array = [];  //position array.

process.stdin.resume();
var my_carrier = carrier.carry(process.stdin);

my_carrier.on('line',function (line) {
	if(line.length > 0){
		var arr = line.split(/\s+/);
		var t = parseFloat(arr[val_col]);

		if (!isNaN(t)) {
			val_array.push(t);
			pos_array.push(arr[pos_col])
		}else if( arr[pos_col] == pos ){ //keep the selected sites.
			val_array.push(0);
			pos_array.push(arr[pos_col]);
		};
		
		// console.log(arr[val_index]);
	};
})

my_carrier.on('end',function(){
	// console.log(val_array);

    regionNum=0
	for (var i = 0; i < pos_array.length; i++) {
		if (pos_array[i] == pos) {
			// total ++;
            regionNum++

			if (checkSelectionAndCheckLocating(i-flanking_size, i + flanking_size,i) == true) {
				ok++;
			};
		};
	};
	
    console.log("#Affected region: "+regionNum)
	console.log("#total positive selceted: "+total);
	console.log("#powered: "+ok);

	console.log(ok/total);
});


/**
 * Check whether this region are positively selected. 
 * Region include the start and end point.
 * 
 * And check whether correted located the selected causal sites.
 */
function checkSelectionAndCheckLocating(start,end, s_index){
	// console.log("-------------------");
	var temp_arr = [];
	var s_val = 0;

	var n = 0;
	for (var i = start; i <= end; i++) {
		// console.log("I: " + val_array[i]);
		temp_arr.push(Math.abs(val_array[i]));

		if (i == s_index){
			if(!isNaN(val_array[i])){
				s_val = Math.abs(val_array[i]);
			}
		}

		if (Math.abs(val_array[i]) >= threshold ) {
			n++;
		};
	};

	if (n > critical) {
		total ++ ; // this sites are positively selected.

        temp_arr.sort(function(a,b){return b-a;});
		// console.log(temp_arr);
		// console.log(s_val);

		if(temp_arr[top] <= s_val){
			return true;
		}else{
			return false;
		}
	};

	return false;
}

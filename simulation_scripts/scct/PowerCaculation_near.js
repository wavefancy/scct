/*
 * Power caculation. 
 * Using a windowed approach. A window assumed to be experienced natural selection if the number of value bigger than the threshold is more than the 0.95 critical value in neutral scenario.
 * >= threshold.
 * > critical value.
 * SNP with NA value will be removed automatically, except those SNP with selection acting on.
 * IF the position cann't be founded, find the most near position.
 * pos - pos[i] > 0 && pos - pos[i+1] < 0, choose index i.
 * 
 * MaxiumCount[optional]: set Maxium number of data set you want to test.
 */

var carrier = require('carrier');

var flanking_size = 25; //flanking size
var threshold = 2.0;
var critical = 7; //critical value.
var pos = 0.5000000000;  // selection position
var pos_col = 1; //postion column
var val_col = 5; //value column.

if (process.argv.length < 8 || process.argv.length > 9) {
	console.log("--------------------------")
	console.log("PowerCaculation, version 1.0, wavefancy@gmail.com")
	console.log("Usages:")
	console.log("parameter1(int): flanking size")
	console.log("parameter2(float): threshold size")
	console.log("parameter3(float): critical value")
	console.log("parameter5(String): position")
	console.log("parameter5(int): position column")
	console.log("parameter5(int): value column")
	console.log("parameter6(int)[optional]: MaximumCount")
	console.log("[Column index start from 1.]")
	console.log("--------------------------")

	process.exit(-1);
};

flanking_size = parseInt(process.argv[2]);
threshold = parseFloat(process.argv[3]);
critical = parseFloat(process.argv[4]);
pos = parseFloat(process.argv[5]);
pos_col = parseInt(process.argv[6]) -1;
val_col = parseInt(process.argv[7]) -1;
m_count = parseInt(process.argv[8]);

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
			pos_array.push(parseFloat(arr[pos_col])) //parse position to float.

		}else if( arr[pos_col] == pos ){ //keep the selected sites.
			val_array.push(0);
			pos_array.push(arr[pos_col]);
		};
		
		// console.log(arr[val_index]);
	};
})

my_carrier.on('end',function(){
	// console.log(val_array);

	for (var i = 0; i < pos_array.length; i++) {
		
		if (pos_array[i] == pos) {
			total ++;
            //console.error("in");
            //console.error(pos_array[i]);

			if (checkSelection(i-flanking_size, i + flanking_size) == true) {
				ok++;
			};
		}else if (pos - pos_array[i] > 0 && pos - pos_array[i+1] < 0) { //find most near position.
			total ++;
			//console.error("--"+pos_array[i]);

			if (checkSelection(i-flanking_size, i + flanking_size) == true) {
				ok++;
                //console.log(pos_array[i]);
			};
		};

        //If set, stop, when test MaximumCount(m_count) data set.
        if(! isNaN(m_count)){
            if(total == m_count)
                break;
        }
	};
	
	console.log("#total: "+total);
	console.log("#powered: "+ok);

	console.log(ok/total);
});


/**
 * Check whether this region are positively selected. 
 * Region include the start and end point.
 */
function checkSelection(start,end){
	// console.log("-------------------");
	var n = 0;
	for (var i = start; i <= end; i++) {
		// console.log("I: " + val_array[i]);

		if (Math.abs(val_array[i]) >= threshold ) {
			n++;
		};
	};

	if (n > critical) {
		return true;
	}else{
		return false;
	};
}

#Compute all iHS for selected data.

rs="computeiHS.R"
ss=`seq 900 100 2000`
for pa in ${ss}
do
    echo "R --slave --no-save s${pa} < ${rs}"
done

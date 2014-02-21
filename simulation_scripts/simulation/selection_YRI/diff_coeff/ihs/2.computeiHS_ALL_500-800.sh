#Compute all iHS for selected data.

rs="computeiHS.R"
ss=`seq 500 100 800`
for pa in ${ss}
do
    echo "R --slave --no-save s${pa} < ${rs}"
done

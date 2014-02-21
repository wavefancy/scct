#Compute all iHS for selected data.

rs="~/scct/computeiHS_alt.R"
ss=`seq 500 100 2000`
for pa in ${ss}
do
    echo "R --slave --no-save s${pa} < ${rs}"
done

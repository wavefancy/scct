#Compute all iHS for selected data.

rs="~/scct/computeiHS_alt.R"
ss="10 50 100"
for pa in ${ss}
do
    echo "R --slave --no-save s${pa} < ${rs}"
done

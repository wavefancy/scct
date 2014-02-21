#Compute all iHS for selected data.

rs="~/scct/computeiHS_alt.R"
ss="0.01 0.1 0.5"
for pa in ${ss}
do
    echo "R --slave --no-save s${pa} < ${rs}"
done

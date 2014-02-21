#Compute all iHS for selected data.

rs="computeiHS.R"

for pa in n.ceu
do
    R --slave --no-save ${pa} < ${rs}
done

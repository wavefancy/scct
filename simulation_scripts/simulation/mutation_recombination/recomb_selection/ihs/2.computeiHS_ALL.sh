#Compute all iHS for selected data.

rs="./computeiHS_alt.R"
ss=`cat ../para.sh`
for pa in ${ss}
do
    echo "R --slave --no-save s${pa} < ${rs}"
done

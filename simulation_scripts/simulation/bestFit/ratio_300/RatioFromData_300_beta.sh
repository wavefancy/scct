# Estimate ratio from emprical data.

inf="../yri.id60.gz"

#ss=`seq 50 40 1000`
ss="300"
for s in ${ss}
do
echo "zcat ${inf} | java -jar -Xmx2G ~/jars/ComputeRatioFromRealDataMs.beta0.1.jar 20 ${s} -a -x 2>ratio_empirical_${s}_beta.txt"
done

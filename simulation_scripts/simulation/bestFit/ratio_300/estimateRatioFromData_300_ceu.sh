# Estimate ratio from emprical data.

inf="../ceu.id60.gz"

#ss=`seq 50 40 1000`
ss="300"
for s in ${ss}
do
echo "zcat ${inf} | java -jar -Xmx2G ~/jars/RatioEstimateFromRealData.V1.0.jar 20 ${s} 0 >ratio_empirical_${s}_ceu.txt"
done

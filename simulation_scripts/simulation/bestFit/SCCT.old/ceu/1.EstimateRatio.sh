# Estimate ratio from real data.

inf="../../ceu.id60.gz"

echo "zcat ${inf} | java -jar -Xmx2G ~/jars/RatioEstimateFromRealData.V1.0.jar 20 300 0 >ratio_empirical_300.txt"

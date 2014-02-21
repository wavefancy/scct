# Convert data to REHH

inf="../../../ceu.id60.gz"

echo "mkdir n.ceu && cd n.ceu && zcat ${inf} | java -jar -Xmx1G ~/scct/RemoveMAFfromMS.V1.0.jar 0.05 | java -jar -Xmx5G ~/scct/ConvertMStoREHH.V1.0.jar 3000000 n.ceu"

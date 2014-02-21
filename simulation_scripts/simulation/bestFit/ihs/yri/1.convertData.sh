# Convert data to REHH

inf="../../../yri.id"
#inf="../../../yri.id60.gz"
id=`cat para.sh`
for i in ${id}
do

#echo "mkdir -p n.yri"
echo "mkdir -p s${i} && cd s${i} && zcat ${inf}${i}.gz | java -jar -Xmx1G ~/scct/RemoveMAFfromMS.V1.0.jar 0.05 | java -jar -Xmx5G ~/scct/ConvertMStoREHH.V1.0.jar 3000000 s${i}"

done

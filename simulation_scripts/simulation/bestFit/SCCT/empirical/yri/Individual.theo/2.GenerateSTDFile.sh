# Generate standardize file.

#mutations.f550.gz

ss=`cat para.sh`
for l in ${ss}
do
    echo "zcat ratio.id${l}.gz | java -jar -Xmx1G ~/scct/StandardizeFileGenerator.V1.0.jar 2 3 0.01 > std.id${l}.txt"
done

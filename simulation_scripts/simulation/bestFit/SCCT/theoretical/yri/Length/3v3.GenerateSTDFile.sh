# Generate standardize file.

#mutations.f550.gz

ss=`seq 50 50 1000`
for l in ${ss}
do
    echo "zcat ratio.f${l}.gz | java -jar -Xmx1G ~/scct/StandardizeFileGenerator.V1.0.jar 2 3 0.01 > std.f${l}.txt"
done

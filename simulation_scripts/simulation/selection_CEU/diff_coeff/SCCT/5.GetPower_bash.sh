# Get power for each length

#std.ratio.f950.gz
ss=(`seq 500 100 2000`)
#cv=(8 8 8 8 8 8 8)
out="power.txt"
echo ">${out}"
for ((i=0; i<${#ss[@]}; i++))
{
    echo "echo ${ss[i]} >>${out}"
    echo "zcat std.ratio.${ss[i]}.gz | node ~/scct/PowerCaculation_near.js 25 2.0 9 0.5000000000 1 4 >>${out}"
    echo "echo '' >>${out}"
}
#    echo "zcat ratio.f${l}.gz | java -jar -Xmx1G ~/jars/StandardizeFromFile.V1.0.jar 2 3 std.f${l}.txt | gzip >std.ratio.f${l}.gz "

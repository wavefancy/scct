# Get power for each length

#std.ratio.f950.gz
ss=(`cat para.sh`)
cv=(8 8 8 8 8 8 8 8 8 8 8 8 8 8 8 8 7 7 7)
out="power.txt"
echo ">${out}"
for ((i=0; i<${#cv[@]}; i++))
{
    echo "echo ${ss[i]} >>${out}"
    echo "zcat std.ratio.f${ss[i]}.gz | node ~/scct/PowerCaculation_near.js 25 2.0 ${cv[i]} 0.5000000000 1 4 >>${out}"
    echo "echo '' >>${out}"
}
#    echo "zcat ratio.f${l}.gz | java -jar -Xmx1G ~/jars/StandardizeFromFile.V1.0.jar 2 3 std.f${l}.txt | gzip >std.ratio.f${l}.gz "

# Compute power

#t_100.gz
ss=(`cat ../para.sh`)
cv=(-1.4954969609 -1.3658082721 -1.2785058415 -1.2167051089)
out="power.txt"
rm ${out}

for ((i=0;i<${#ss[@]};i++))
{
    echo "${ss[i]}" >> ${out}
    zcat t_${ss[i]}.gz | node ~/scct/PowerCaculation_Single.js 1 ${cv[i]} 1 -1 >> ${out} 
    echo "" >> ${out}
}

# Compute power

#t_100.gz
ss=(`cat ../para.sh`)
cv=(-1.1728796476 -1.2593204096 -1.3792210691 -1.412562122)
out="power.txt"
rm ${out}

for ((i=0;i<${#ss[@]};i++))
{
    echo "${ss[i]}" >> ${out}
    zcat t_${ss[i]}.gz | node ~/scct/PowerCaculation_Single.js 1 ${cv[i]} 1 -1 >> ${out} 
    echo "" >> ${out}
}

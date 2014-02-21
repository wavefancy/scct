# Compute power

#t_100.gz
ss=(`cat ../para.sh`)
cv=(-1.480660404 -1.5149176489 -1.5458675504 -1.5612769532)
out="power.txt"
rm ${out}

for ((i=0;i<${#ss[@]};i++))
{
    echo "${ss[i]}" >> ${out}
    zcat t_${ss[i]}.gz | node ~/scct/PowerCaculation_Single.js 1 ${cv[i]} 1 -1 >> ${out} 
    echo "" >> ${out}
}

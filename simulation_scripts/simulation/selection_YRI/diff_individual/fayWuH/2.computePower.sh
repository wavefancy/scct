# Compute power

#t_100.gz
ss=(`cat ../para.sh`)
cv=(-1.3112855771 -1.3298317941 -1.3374930304 -1.3396650384 -1.3264898456 -1.3060223899 -1.3145373432)
out="power.txt"
rm ${out}

for ((i=0;i<${#ss[@]};i++))
{
    echo "${ss[i]}" >> ${out}
    zcat t_${ss[i]}.gz | node ~/scct/PowerCaculation_Single.js 1 ${cv[i]} 1 -1 >> ${out} 
    echo "" >> ${out}
}

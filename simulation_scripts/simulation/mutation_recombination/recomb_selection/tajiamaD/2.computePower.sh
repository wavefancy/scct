# Compute power

#t_100.gz
ss=(`cat ../para.sh`)
cv=(-1.5764041207 -1.5506266895 -1.522248215 -1.4989580782)
out="power.txt"
rm ${out}

for ((i=0;i<${#ss[@]};i++))
{
    echo "${ss[i]}" >> ${out}
    zcat t_${ss[i]}.gz | node ~/scct/PowerCaculation_Single.js 1 ${cv[i]} 1 -1 >> ${out} 
    echo "" >> ${out}
}

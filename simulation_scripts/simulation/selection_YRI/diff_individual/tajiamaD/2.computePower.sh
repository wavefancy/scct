# Compute power

#t_100.gz
ss=(`cat ../para.sh`)
cv=(-1.465048525 -1.5335757695 -1.5717176417 -1.6019250811 -1.6179540796 -1.6369987489 -1.6507765558)
out="power.txt"
rm ${out}

for ((i=0;i<${#ss[@]};i++))
{
    echo "${ss[i]}" >> ${out}
    zcat t_${ss[i]}.gz | node ~/scct/PowerCaculation_Single.js 1 ${cv[i]} 1 -1 >> ${out} 
    echo "" >> ${out}
}

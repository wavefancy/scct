# Compute power

#t_100.gz
ss="0.1 0.01 0.5"
out="power.txt"
rm ${out}
for s in ${ss}
do
    echo "${s}" >> ${out}
    zcat t_${s}.gz | node ~/scct/PowerCaculation_Single.js 1 -1.5335757695 1 -1 >> ${out} 
done

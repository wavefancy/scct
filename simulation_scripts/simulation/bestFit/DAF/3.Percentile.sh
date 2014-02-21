# Compute percentile

ss=`seq 30 30 210`
out="percentile.txt"
>${out}
for s in ${ss}
do
    echo ${s} >> ${out}
    zcat yri_ceu.daf.id${s}.gz | node ~/scct/Percentile.js 4 0.95 -1 >>${out}
    echo "" >> ${out}
done

# Compute Fay&Wu's H.

ss=`cat ../para.sh`
out="0.05percentile.txt"
>${out}
for s in ${ss}
do
    echo ${s} >> ${out}
    zcat t_${s}.gz | node ~/scct/Percentile.js 2 0.05 1 >> ${out}
    echo "" >> ${out}
done


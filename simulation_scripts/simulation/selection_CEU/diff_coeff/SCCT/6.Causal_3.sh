# Compute power for each window length.

#inf="std.ratio.id*.gz"

ss=`seq 500 100 2000`
out="causal_3.txt"
>${out}
for s in ${ss}
do
    echo ${s} >> ${out}
    zcat std.ratio.${s}.gz | node ~/scct/LocateCausalSite.js 25 2 9 0.5000000000 1 4 3 >>${out}
    echo "" >> ${out}
done   

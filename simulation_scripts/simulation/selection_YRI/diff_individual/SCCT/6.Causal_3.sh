# Compute power for each window length.

#inf="std.ratio.id*.gz"

ss=`seq 30 30 210`
out="causal_3.txt"
>${out}
for s in ${ss}
do
    echo ${s} >> ${out}
    zcat std.ratio.id${s}.gz | node ~/scct/LocateCausalSite.js 25 2 8 0.5000000000 1 4 3 >>${out}
    echo "" >> ${out}
done   

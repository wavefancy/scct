# Compute power for each window length.

#std.ratio.f950.gz
ss=`seq 50 50 1000`
out="causal_5.txt"
>${out}
for s in ${ss}
do
    echo ${s} >> ${out}
    zcat std.ratio.f${s}.gz | node ~/scct/LocateCausalSite.js 25 2 8 0.5000000000 1 4 5 >>${out}
    echo "" >> ${out}
done   

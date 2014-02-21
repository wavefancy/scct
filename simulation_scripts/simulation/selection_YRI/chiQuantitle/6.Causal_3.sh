# Compute power for each window length.

#std.ratio.f950.gz
ss=`cat para.sh`
out="causal_3.txt"
>${out}
for s in ${ss}
do
    echo ${s} >> ${out}
    zcat std.ratio.f${s}.gz | node ~/scct/LocateCausalSite.js 25 2 8 0.5000000000 1 4 3 >>${out}
    echo "" >> ${out}
done   

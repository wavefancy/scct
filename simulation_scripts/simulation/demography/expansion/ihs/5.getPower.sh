# Compute power for each window length.

#id90_ihs.txt
#inf="s*_ihs.txt"
ss="10 50 100"
#l=5
out="power.txt"
>${out}
for s in ${ss}
do
    echo ${s} >> ${out}
    cat s${s}_ihs.txt | node ~/scct/PowerCaculation_near.js 25 2 8 1500000 3 4 >>${out}
done   

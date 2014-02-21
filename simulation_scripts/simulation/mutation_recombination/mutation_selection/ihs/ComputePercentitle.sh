# Compute prcentile.

inf="n.yri_ihs.txt"
out="percentile.txt"
>${out}

for f in ${inf}
do
    echo "${f}" >>${out}
    cat ${f} | node ~/node/Percentile.js 4 0.95 -1 >> ${out} 
done

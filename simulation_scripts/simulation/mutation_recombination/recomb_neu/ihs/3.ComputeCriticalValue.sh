# Compute Critical value for a window.

inf="n.yri_ihs.txt"
out="critical.value.txt"
>${out}

for f in ${inf}
do
    echo "${f}" >> ${out} 
    cat ${f} | node ~/scct/ComputeWindowedCriticalValue.js 51 51 2 4 >> ${out}
done

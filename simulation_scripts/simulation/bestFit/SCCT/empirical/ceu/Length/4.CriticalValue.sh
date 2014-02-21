#Compute critical value.

#mutations.f550.gz

ss="300"
out="critical.txt"
>${out}

for l in ${ss}
do
    echo ${l} >>${out}
    zcat std.ratio.f${l}.gz | node ~/scct/ComputeWindowedCriticalValue.js 51 51 2.0 4 >>${out}

    echo "" >>${out}
done

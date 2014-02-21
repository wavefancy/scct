# Compute power for each window length.

#inf="std.ratio.id*.gz"

p=(0.316667 0.291666 0.272222 0.258333 0.25 0.241667 0.235714)
ss=(`seq 30 30 210`)
out="causal_5.txt"
echo ">${out}"
for((i=0;i<${#ss[@]};i++)){

    echo "echo ${ss[i]} >> ${out}"
    echo "zcat yri_ceu.daf.id${ss[i]}.gz | node ~/scct/LocateCausalSite.js 25 ${p[i]} 8 0.5000000000 1 4 5 >>${out}"
    echo "echo '' >> ${out}"

}

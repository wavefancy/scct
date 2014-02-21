# Simulate the expansion model of YRI.

# Ne = 100000, u = 1.5 * 10^-8, r = 1.3 * 10^-8. L = 3m.
# theta = 4*Ne*u*L = 4*100000*1.5*10^-8*3*10^6 = 18000
# p = 4*Ne*r*L = 4 * 100000 * 1.3 * 10^-8 * 3 * 10^6 = 15600

# AF expansion:
# 200g/(4*100000) = 0.0005
# Ne_Africa_before expansion 24000 = 0.24

# EU expansion:
# 350/(4*100000) = 0.000875
# Ne_European_before expansion 7700 = 0.077

# EU out of africa: 3500/(4*100000) = 0.00875

# African ancestral expansion:
# 17000/(4*100000) = 0.0425
# Ne_before expansion: 12500 = 0.125

# Extent africa recent expansion to 1000 generation
# Time: 0g - 1000g: 0 - 0.0025
# Coefficient 10 50 100

ss="10 50 100"
for s in ${ss}
do
    #b=`echo "${s}*0.24" | bc -l`

    echo "java -jar -Xmx30G ~/scct/msms.jar 240 300 -t 18000 -r 15600 -N 100000 -I 2 120 120 -en 0 1 ${s} -en 0.0025 1 0.24 -en 0.000875 2 0.077 -ej 0.00875 2 1 -en 0.0425 1 0.125 -threads 15 | gzip > AFR_CEU.exp.${s}.gz"
done

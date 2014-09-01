
# Generate scale ratio file.
zcat counts.gz | grep -i 'ok' | python ComputeEmpiricalAlphaV1.0.py 2 3 4 >scale_ratio.txt

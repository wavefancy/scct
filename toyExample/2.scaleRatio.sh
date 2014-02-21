
# Generate scale ratio file.
zcat counts.gz | grep -i 'ok' | python ComputeScaleRatioV1.1.py 2 3 4 >scale_ratio.txt


#!/usr/bin/env python

'''
    Select Fixed Number of norepeat genes.

    @author: wavefancy@gmail.com
    @sine: 2013 - 06 - 25
    @version: 1.0
    
'''

import sys

def help():
    sys.stderr.write('''
                     
    Select Fixed Number of norepeat genes.
    
    @author: wavefancy@gmail.com
    @sine: 2013 - 06 - 25
    @version: 1.0
    
    @usages:
    para1: select gene number.
    
    @note:
      1. Read gene list line by line from sys.stdin
      2. One single line can contain multiple genes, seprarted by ','.
      3. Gene list should be ordered.
    \n''')

    sys.exit(-1)

class P(object): #parameters
    sel_num = 0 #gene number want to selected.
    
    
class App(object):
    
    def __init__(self, ):
        #print('test')
        self.gene_set = set()
    
    def run(self, ):
        
        for line in sys.stdin:
            line = line.strip()
            if(len(line) > 0):
                ss = line.split(',')
                map(self.addGene,ss)
        
        ''''--IF lines less than setting in parameter, output the cached results---'''
        sys.stdout.write('\n'.join(self.gene_set))
        sys.stdout.write('\n')       
        
    def addGene(self,gene):
        if(len(self.gene_set) >= P.sel_num):
            
            sys.stdout.write('\n'.join(self.gene_set))
            sys.stdout.write('\n')
            
            sys.exit(0)
        else:
            self.gene_set.add(gene)
            

if __name__ == '__main__':
    if(len(sys.argv) != 2):
        help()
    else:
        P.sel_num = int(sys.argv[1])
        
        app = App()
        app.run()
        
        
        
    


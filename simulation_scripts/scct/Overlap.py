#!/usr/bin/env python

'''
    Count the overlap between list.
    Generate the data used for ven graphy.
    
    
    @author: wavefancy@gmail.com
    @sine: 2013 - 06 - 16
    @version: 1.0
    
'''

import sys
import itertools as it

def help():
    sys.stderr.write('''
                     
    Count the overlap between list.
    Generate the data used for ven graphy.
    
    
    @author: wavefancy@gmail.com
    @sine: 2013 - 06 - 16
    @version: 1.0
    
    @usages:
    para1-n: gene_list_files (one gene one line)
    
    \n''')
    sys.exit(-1)

class P(object): #parameters
    list_files = []
    
    
class App(object):
    
    def run(self, ):
        
        lists = []
        #read lists from file.
        for f in P.list_files:
            elements = set() #non-repeat elements
            for line in open(f):
                line = line.strip() #every element as a new line.
                if(len(line) > 0):
                    elements.add(line)
            
            lists.append(elements)
        
        for i in xrange(1,len(lists)+1):
            com = it.combinations(range(len(lists)),i)
            
            for cc in com:
                
                #prepare the new lists.
                n_lists = []
                for j in cc:
                    n_lists.append(lists[j])
                
                sys.stdout.write(str( map(lambda x:x+1, cc) ))
                sys.stdout.write('\t')
                sys.stdout.write(str( self.overlap(n_lists) ))
                sys.stdout.write('\n')
    
    def overlap(self,lists):
        '''
            Count the number of overlap between lists.
            
            lists: [set(),set()...], list of elements set.
           
            if len(lists) == 1, return the lenght of this list.
            else:
                return the number of overlap between list.
        '''
        if(len(lists) == 1):
            return len(lists[0])
        elif(len(lists) == 2):
            return len( lists[0].intersection( lists[1] ) )
        else:
            #print(lists)
            
            n_lists = lists[0:len(lists) -2]
            n_lists.append( lists[-1] & lists[-2] )
            
            #print(n_lists)
            return self.overlap(n_lists)
            
if __name__ == '__main__':
    if(len(sys.argv) <= 1):
        help()
    else:
        for f in sys.argv[1:]:
            P.list_files.append(f)
        
        app = App()
        app.run()
        
        
        
    


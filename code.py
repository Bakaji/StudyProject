import os

def delete_extra(path):
    arr=os.listdir(path)
    for e in arr:
        element=os.path.join(path,e)
        if os.path.isfile(element):
            print(f"file {element}")
            if(element.endswith('.scss') or element.endswith('.css.map') or element.endswith('.ico')):
                os.remove(element)
        else:
            print(f"dir {element}")
            delete_extra(element)
        

delete_extra('./target')
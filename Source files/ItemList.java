package List;

import java.util.*;

import Others.Item;


public abstract class ItemList implements Iterable<Item>
{
    private LinkedList<Node> list;
    private Comparator<Node> comp;

    public ItemList()
    {
        list = new LinkedList<>();
        comp = new Comparator<Node>()
        {
            @Override
            public int compare(Node n1, Node n2)
            {
                Item item1 = (Item) n1.getValue();
                Item item2 = (Item) n2.getValue();

                int value = item1.getPrice().compareTo(item2.getPrice());

                if (value == 0)
                    return item1.getName().compareTo(item2.getName());

                return value;
            }
        };
    }

    public ItemList(Comparator comp)
    {
        list = new LinkedList<>();
        this.comp = comp;
    }

    public Comparator comparator()
    {
        return comp;
    }

    static class Node<Item>
    {
        private Item item;
        private Node<Item> prev;
        private Node<Item> next;

        public Node(Item item, Node prev, Node next)
        {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }

        public Item getValue()
        {
            return item;
        }
    }

    class ItemIterator implements ListIterator
    {
        private Node<Item> current;

        public ItemIterator()
        {
            if (list.size() > 0)
                current = list.getFirst();
            else
                current = null;
        }

        @Override
        public boolean hasNext()
        {
            return (current != null);
        }

        @Override
        public Item next()
        {
            Node<Item> temp = current;
            current = current.next;
            return temp.getValue();
        }

        @Override
        public boolean hasPrevious()
        {
            return (current.prev == null);
        }

        @Override
        public Item previous()
        {
            current = current.prev;
            return current.getValue();
        }

        @Override
        public int nextIndex()
        {
            return 0;
        }

        @Override
        public int previousIndex()
        {
            return 0;
        }

        @Override
        public void remove()
        {

        }

        @Override
        public void set(Object o)
        {

        }

        @Override
        public void add(Object o)
        {

        }
    }

    @Override
    public ListIterator<Item> iterator()
    {
        return new ItemIterator();
    }

    public boolean add(Item element)
    {
        Node node = new Node(element, null, null);

        if (list.isEmpty())
        {
            list.add(node);
            return true;
        }

        for (Node nd : list)
        {
            if (comp.compare(node, nd) < 0)
            {
                if (nd.prev != null)
                    nd.prev.next = node;

                node.prev = nd.prev;
                node.next = nd;

                nd.prev = node;

                list.add(list.indexOf(nd), node);
                return true;
            }
        }

        list.getLast().next = node;
        node.prev = list.getLast();
        list.add(node);

        return true;
    }

    public boolean addAll(Collection<Item> c)
    {
        for (Item s : c)
            add(s);

        return false;
    }

    public Item getItem(int index)
    {
        int i = 1;
        for (Node node : list)
        {
            if (index == i)
                return (Item) node.getValue();
            i++;
        }

        return null;
    }

    public Node<Item> getNode(int index)
    {
        int i = 1;
        for (Node node : list)
        {
            if (index == i)
                return node;
            i++;
        }

        return null;
    }

    public int indexOf(Item item)
    {
        int i = 1;
        for (Node node : list)
        {
            if ((node.getValue()).equals(item))
            {
                return i;
            }

            i++;
        }

        return -1;
    }

    public int indexOf(Node<Item> node)
    {
        int i = 1;
        for (Node nd : list)
        {
            if (nd.equals(node))
            {
                return i;
            }

            i++;
        }

        return -1;
    }

    public boolean containts(Node<Item> node)
    {
        return (indexOf(node) > -1);
    }

    public boolean containts(Item item)
    {
        return (indexOf(item) > -1);
    }

    public Item remove(int index)
    {
        index--;
        Item myItem = (Item) list.get(index).getValue();
        list.remove(index);

        if (index == 0) // removing first
        {
            try
            {
                list.getFirst().prev = null;
            }
            catch (Exception ex)
            {
                list.clear();
            }
        }
        else if (index == size()) // removing last
        {
            list.getLast().next = null;
        }
        else // removing in between
        {
            list.get(index - 1).next = list.get(index);
            list.get(index).prev = list.get(index - 1);
        }

        return myItem;
    }

    public boolean remove(Item item)
    {
        int i = 1;
        for (Node node : list)
        {
            if (node.getValue().equals(item))
            {
                remove(i);
                return true;
            }
            i++;
        }

        return false;
    }

    public boolean removeAll(Collection<? extends Item> collection)
    {
        for (Object obj : collection)
            return remove((Item) obj);
        return false;
    }

    public void clearList()
    {
        list.clear();
    }

    public int size()
    {
        return list.size();
    }

    public boolean isEmpty()
    {
        return list.isEmpty();
    }

    public Double getTotalPrice()
    {
        Double sum = new Double(0);

        for (Node node : list)
            sum += ((Item) (node.getValue())).getPrice();

        return sum;
    }
}

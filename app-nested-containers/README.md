**What your're seeing here**
- There are nested containers of the same type
- Every level can have its routing to a Red, Green, or Blue subtree
- When you hit `Next`, that level pushes a new Routing to its own back stack, resulting in a new subtree
- The previous state of that level is kept in a back stack
- When you hit back on the device, the lowest level that has any screen history in its back stack will pop, and the previous subtree gets restored

Not shown in this gif (should capture again), but actual code also has local state on every level (a counter), that is persisted and restored from back stack.

![](https://i.imgur.com/w3Lr2IE.gif)

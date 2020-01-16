**What you're seeing here**
- There's a whole app hierarchy represented solely with Compose
- Any level that needs a back stack can have one
- `Root` level switches between `LoggedOut` / `LoggedIn` routing
- Once logged in, `LoggedOut` flow is removed from back stack, you cannot go back by pressing back button
- `LoggedOut` flow has its own routing of registration screens with its own back stack
- `Loggedin` subtree has its own routing of `Menu` + main screens (`Gallery`, `News`, `Profile`), with back stack for demonstration purposes
- `Gallery` subtree has its own routing with `Album list`, `Album view`, `Photo view` with back stack
- Once there's no more back stack to pop on back press, Android default action happens (Activity finishes)

![](https://i.imgur.com/4h22NyZ.gif)

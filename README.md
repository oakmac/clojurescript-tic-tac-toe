# ClojureScript Tic-Tac-Toe

A game of tic-tac-toe implemented using ClojureScript.

Used as a teaching exercise for the [Professional ClojureScript course](https://cljs.pro).

## Approach Used

- represent UI state in an atom
- [hiccups] for HTML generation
- separate game logic into it's own namespace

[hiccups]:https://github.com/macchiato-framework/hiccups

## Development Setup

```sh
## install node_modules/ folder (one-time setup)
npm install

## start shadow-cljs server
npx shadow-cljs server

# (in a separate terminal window or tab)

## watch the build for development
npx shadow-cljs watch app

## OR

## create a release for production
npx shadow-cljs release app

## (if using watch) connect a browser REPL
npx shadow-cljs cljs-repl app
```

## License

[ISC License](LICENSE.md)

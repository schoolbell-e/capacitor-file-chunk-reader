# capacitor-file-chunk-reader

can read only a part of a file

## Install

```bash
npm install capacitor-file-chunk-reader
npx cap sync
```

## API

<docgen-index>

* [`readFileChunk(...)`](#readfilechunk)
* [`readFileSize(...)`](#readfilesize)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### readFileChunk(...)

```typescript
readFileChunk(params: ReadFileChunkParams) => Promise<{ data: string; }>
```

| Param        | Type                                                                |
| ------------ | ------------------------------------------------------------------- |
| **`params`** | <code><a href="#readfilechunkparams">ReadFileChunkParams</a></code> |

**Returns:** <code>Promise&lt;{ data: string; }&gt;</code>

--------------------


### readFileSize(...)

```typescript
readFileSize(params: { path: string; }) => Promise<{ size: number; }>
```

| Param        | Type                           |
| ------------ | ------------------------------ |
| **`params`** | <code>{ path: string; }</code> |

**Returns:** <code>Promise&lt;{ size: number; }&gt;</code>

--------------------


### Interfaces


#### ReadFileChunkParams

| Prop        | Type                |
| ----------- | ------------------- |
| **`path`**  | <code>string</code> |
| **`start`** | <code>number</code> |
| **`end`**   | <code>number</code> |

</docgen-api>
